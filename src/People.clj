(ns People
  (:use Collections)
  (:use clojure.contrib.def))

(def relationships {})

(defn knows? [person other-person]
  (= other-person (get relationships person)))

(defn introduce [person other-person]
  (def relationships (assoc relationships person other-person)))

(defn person [name] {:name name})