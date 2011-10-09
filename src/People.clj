(ns People
  (:use Collections)
  (:use clojure.contrib.def))

(keyword :now-knows )

(defn knows? [person other-person]
  (person :already-knows other-person))

(defn introduce [person other-person]
  (person :now-knows other-person))

(defn person [name]
  (def my-contacts [])
  (fn myself [& args]
    (let [arguments (apply hash-map args)]
      (if (contains? arguments :now-knows )
        (def my-contacts (conj my-contacts (:now-knows arguments)))
        (list-contains? my-contacts (:already-knows arguments))))))
