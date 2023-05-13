# Bytes Hybrid Recommender System Algorithm

**Sistem Rekomendasi Hybrid Bytes** menggabungkan model `content-based` dan `collaborative filtering` untuk memberikan **rekomendasi yang personal untuk pengguna**.

* `content_based_model`, menggunakan kelas `TfidfVectorizer` dari pustaka 
`sklearn` untuk mengonversi fitur teks dari setiap kursus (judul, deskripsi, tag, dan ringkasan) menjadi vektor numerik menggunakan fungsi `indo_analyzer` (note: untuk saat ini tidak digunakan karena pertimbangan waktu response) sebagai pengolah. Kemudian, ia **mengembalikan vectorizer yang telah dilatih dan matriks fitur yang telah diubah**.

* `collaborative_filtering_model` mendefinisikan algoritma `SVD` dari pustaka `surprise` sebagai model `collaborative filtering` dan melatihnya pada set pelatihan yang diberikan menggunakan metode `fit`. Kemudian, ia **mengembalikan model yang telah dilatih**.

* `content_based_recommendations` mengambil model content-based yang telah dilatih, indeks kursus, dan matriks kemiripan kosinus antara kursus sebagai input. Fungsi ini menghitung kemiripan kosinus antara kursus yang diberikan dan semua kursus lainnya, mengurutkannya secara menurun, dan **mengembalikan daftar `num_recommendations` kursus teratas dengan skor kemiripan kosinusnya**.

* `hybrid_recommendations` mengambil data frame kursus, ID pengguna, model collaborative filtering yang telah dilatih, matriks kemiripan kosinus, dan jumlah rekomendasi sebagai input. Pertama, ia menggunakan `collaborative filtering` untuk memprediksi penilaian pengguna untuk semua kursus dan mengurutkannya secara menurun. Kemudian, ia menggunakan `content-based filtering` untuk mencari kursus yang mirip dengan yang direkomendasikan oleh `collaborative filtering`, dengan tidak memasukkan kursus yang telah direkomendasikan sebelumnya. Akhirnya, ia menggabungkan `num_recommendations` teratas dari kedua metode dan **mengembalikan indeks kursus yang direkomendasikan**.
