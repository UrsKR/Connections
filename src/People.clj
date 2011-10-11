(ns People
  (:use Collections)
  (:use clojure.contrib.def))

(defn knows-someone-like?
  ([someone other-person]
    (let [names (seq (:relationships someone))]
      (some #(= (:name other-person) (:name %)) names))))

(defn knows? [someone other-person]
  (if (= someone other-person)
    true
    (not (nil? (knows-someone-like? someone other-person)))))

(defn know-each-other? [someone other-person]
  (and (knows? someone other-person) (knows? other-person someone)))

(defn is-linked-to?
  ([someone other-person]
    (is-linked-to? someone other-person []))
  ([someone other-person already-inspected-persons]
    (cond
      (nil? someone) false
      (knows? someone other-person) true
      (list-contains? already-inspected-persons someone) false
      :else (do (def contacts-of-person (:relationships someone))
              (def direct-link (first contacts-of-person))
              (is-linked-to? direct-link other-person (conj already-inspected-persons someone))))))

(def person)

(def introduce
  (fn ([someone other-person]
        (person (:name someone) (conj (:relationships someone) other-person)))
    ([person other-person & more]
      (introduce person other-person)
      (apply introduce person more))))

(defn person
  ([name] (person name []))
  ([name contacts] {:name name, :relationships contacts}))