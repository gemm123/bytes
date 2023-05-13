from flask import Flask, request, jsonify
from flask_cors import CORS
from recommender import preprocess_data
from recommender import collaborative_filtering_model
from recommender import content_based_model
from recommender import hybrid_recommendations
from sklearn.metrics.pairwise import cosine_similarity

app = Flask(__name__)
CORS(app)

@app.route('/get_recommendation', methods=['POST'])
def get_recommendation():
    json_data = request.get_json()
    # Preprocess the data
    df_course, reader, ratings_data, trainset = preprocess_data(json_data)

    # Train collaborative filtering model
    algo_cf = collaborative_filtering_model(trainset)

    # Train content-based filtering model
    tfidf, course_features = content_based_model(df_course)

    # Compute cosine similarities
    cosine_similarities = cosine_similarity(course_features, course_features)

    # Get hybrid recommendations for a given user
    user_id = df_course['UserId'][0]
    hybrid_recs = hybrid_recommendations(df_course, user_id, algo_cf, cosine_similarities, num_recommendations=10)

    # Return recommended courses
    result = df_course.loc[hybrid_recs, ['CourseId', 'Title']]
    return jsonify(result.to_dict('records'))

if __name__ == '__main__':
    app.run(debug=True)
