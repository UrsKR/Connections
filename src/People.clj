(ns People
  (:use Collections)
  (:use clojure.contrib.def))

(def relationships {})

(defn knows? [person other-person]
  (if (= person other-person)
    true
    (not (nil? (list-contains? (get relationships person) other-person)))))

(defn know-each-other? [person other-person]
  (and (knows? person other-person) (knows? other-person person)))

(comment defn is-linked-to? [person other-person]
  (def contacts-of-person (get relationships person))
  (= other-person (get relationships contacts-of-person)))

(def introduce
  (fn ([person other-person]
        (def contacts [])
        (def contacts (conj (get relationships person) other-person))
        (def relationships (assoc relationships person contacts)))
    ([person other-person & more]
      (introduce person other-person)
      (apply introduce person more))))

(defn person [name] {:name name})