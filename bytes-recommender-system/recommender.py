import pandas as pd
import numpy as np
import json

# !pip install Sastrawi
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
# import nltk
# from Sastrawi.StopWordRemover.StopWordRemoverFactory import StopWordRemoverFactory
# from Sastrawi.Stemmer.StemmerFactory import StemmerFactory
from sklearn.feature_extraction.text import TfidfVectorizer

# !pip install surprise
from surprise import SVD
from surprise import Dataset
from surprise import Reader
from sklearn.feature_extraction.text import TfidfVectorizer


# Download NLTK resources
# nltk.download('punkt')
# nltk.download('stopwords')

def preprocess_data(json_data):
    # Load JSON data into a Python dictionary
    json_string = json.dumps(json_data)
    data_dict = json.loads(json_string)
    # Extract the list of dictionaries under the 'data' key
    data_list = data_dict['data']
    # Create a pandas dataframe from the list of dictionaries
    df_course = pd.DataFrame(data_list)

    # Replace boolean values with integers
    print(df_course['UserLikeCourse'].dtype)
    df_course['UserLikeCourse'] = df_course['UserLikeCourse'].replace({False: 0, True: 1})
    df_course['Tag'] = df_course['Tag'].astype(str)
    df_course['Tag'] = df_course['Tag'].str.strip("[]'")
    df_course['UserId'] = df_course['UserId'].astype(str)

    # Load data for collaborative filtering model
    reader = Reader(rating_scale=(0, 1))
    ratings_data = Dataset.load_from_df(df_course[['UserId', 'CourseId', 'UserLikeCourse']], reader)
    trainset = ratings_data.build_full_trainset()
    
    return df_course, reader, ratings_data, trainset


# stemmer_factory = StemmerFactory()
# stemmer = stemmer_factory.create_stemmer()
# stopword_factory = StopWordRemoverFactory()
# stopwords = stopword_factory.get_stop_words()
# # Define analyzer function for TfidfVectorizer
# def indo_analyzer(text):
#     # Tokenize text
#     words = nltk.word_tokenize(text)
#     # Remove stop words
#     words = [word for word in words if word not in stopwords]
#     # Stem words
#     words = [stemmer.stem(word) for word in words]
#     return words

def content_based_model(df_course):
    # Content-based filtering model
    tfidf = TfidfVectorizer(analyzer='word')
    course_features = tfidf.fit_transform(df_course['Title'] + ' ' + df_course['Description'] + ' ' + df_course['Tag'] + ' ' + df_course['Summary'])
    return tfidf, course_features

def collaborative_filtering_model(trainset):
    # Define the SVD algorithm
    algo_cf = SVD()
    # Train the algorithm on the training set
    algo_cf.fit(trainset)
    return algo_cf

def content_based_recommendations(df_course, course_index, cosine_similarities, num_recommendations=10):
    sim_scores = list(enumerate(cosine_similarities[course_index]))
    sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
    sim_scores = sim_scores[1:num_recommendations+1]
    course_indices = [i[0] for i in sim_scores]
    course_ids = df_course['CourseId'].unique().tolist()
    recommended_course_ids = [(course_ids[i], cosine_similarities[course_index][i]) for i in course_indices if i < len(course_ids)]
    return recommended_course_ids

def hybrid_recommendations(df_course, user_id, algo_cf, cosine_similarities, num_recommendations=10):
    # Get collaborative filtering recommendations
    course_ids = df_course['CourseId'].unique().tolist()
    user_ratings = [1 if df_course.loc[(df_course['UserId'] == user_id) & (df_course['CourseId'] == course_id), 'UserLikeCourse'].empty else df_course.loc[(df_course['UserId'] == user_id) & (df_course['CourseId'] == course_id), 'UserLikeCourse'].iloc[0] for course_id in course_ids]
    user_ratings_dict = dict(zip(course_ids, user_ratings))
    testset = [(user_id, course_id, user_ratings_dict.get(course_id, 0)) for course_id in course_ids]
    predictions = algo_cf.test(testset)
    predictions_dict = dict([(p.iid, p.est) for p in predictions])
    cf_course_indices = [course_ids.index(course_id) for course_id in sorted(predictions_dict, key=predictions_dict.get, reverse=True)]

    # Get content-based filtering recommendations, excluding courses that have already been recommended by collaborative filtering
    excluded_courses = set(cf_course_indices[:num_recommendations])
    content_course_ids = df_course['CourseId'].unique()
    content_course_indices = []
    for course_index in cf_course_indices:
        recommended_course_ids = content_based_recommendations(df_course, course_index, cosine_similarities, num_recommendations=1)
        for course_id, _ in recommended_course_ids:
            if course_id not in excluded_courses and course_id in content_course_ids:
                content_course_indices.append(np.where(content_course_ids == course_id)[0][0])
                excluded_courses.add(course_id)
                if len(content_course_indices) == num_recommendations:
                    break
        if len(content_course_indices) == num_recommendations:
            break

    # Combine recommendations
    hybrid_indices = []
    for i in range(num_recommendations):
        if i < len(cf_course_indices):
            hybrid_indices.append(cf_course_indices[i])
        if i < len(content_course_indices):
            hybrid_indices.append(content_course_indices[i])
    return hybrid_indices[:num_recommendations]