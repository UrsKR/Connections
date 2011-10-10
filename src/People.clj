(ns People
  (:use Collections)
  (:use clojure.contrib.def))

(def relationships {})

(defn forget-everyone [] (def relationships {}))

(defn knows? [person other-person]
  (if (= person other-person)
    true
    (not (nil? (list-contains? (get relationships person) other-person)))))

(defn know-each-other? [person other-person]
  (and (knows? person other-person) (knows? other-person person)))

(defn is-linked-to?
  ([person other-person]
    (is-linked-to? person other-person []))
  ([person other-person already-inspected-persons]
    (cond
      (nil? person) false
      (knows? person other-person) true
      (list-contains? already-inspected-persons person) false
      :else (do (def contacts-of-person (get relationships person))
              (def direct-link (first contacts-of-person))
              (is-linked-to? direct-link other-person (conj already-inspected-persons person))))))

(def introduce
  (fn ([person other-person]
        (def contacts [])
        (def contacts (conj (get relationships person) other-person))
        (def relationships (assoc relationships person contacts)))
    ([person other-person & more]
      (introduce person other-person)
      (apply introduce person more))))

(defn person [name] {:name name})