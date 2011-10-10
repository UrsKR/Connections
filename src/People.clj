(ns People
  (:use Collections)
  (:use clojure.contrib.def))

(def relationships {})

(defn knows? [person other-person]
  (= other-person (get relationships person)))

(defn know-each-other? [person other-person]
  (and (knows? person other-person) (knows? other-person person)))

(def introduce
  (fn ([person other-person]
        (def relationships (assoc relationships person other-person)))
    ([person other-person & more]
      (introduce person other-person)
      (apply introduce person more))))

(defn person [name] {:name name})