(ns Connections
  (:use Collections))

(def cooperative "Cooperative")
(def uncooperative "Uncooperative")
(def ignorant "None")

(defn relationship [attitude]
  (fn
    ([] attitude)
    ([other-relationship] (if (= attitude (other-relationship)) cooperative uncooperative))))

(def likes (relationship cooperative))
(def like likes)
(def hates (relationship uncooperative))
(def hate hates)
(def ignores (relationship ignorant))
(def ignore ignores)

(defn- findConnection [query connection1 connection2]
  (if (connection1 query) connection1 connection2))

(defn- shared-bond
  [query1 query2 connection1 connection2]
  (def connectionForQuery1 (findConnection query1 connection1 connection2))
  (def connectionForQuery2 (findConnection query2 connection1 connection2))
  (def link (connectionForQuery1 query1))
  (def attitude1 (connectionForQuery1 query1 link))
  (def attitude2 (connectionForQuery2 query2 link))
  (attitude1 attitude2))

(defn- are-linked?
  ([query1 query2 connection]
    (and (connection query1) (connection query2)))
  ([query1 query2 connection1 connection2]
    (println connection1)
    (println query1)
    (def link1 (connection1 query1))
    (println connection2)
    (println query2)
    (def link2 (connection2 query2))
    (= link1 link2)))

(defn connected
  ([query1 query2 connection]
    (connection query1 query2))
  ([query1 query2 connection1 connection2]
    (if (not (are-linked? query1 query2 connection1 connection2)) ignorant
      (shared-bond query1 query2 connection1 connection2))))

(defn- connect [person relation other-person]
  (fn
    ([query]
      (if (= query person) other-person (if (= query other-person) person)))
    ([query1 query2]
      (let [connectedPersons (list person other-person)]
        (if (list-contains? connectedPersons query1 query2) relation ignores)))))

(defn person [name]
  (def relationships {})
  (fn myself
    ([]
      name)
    ([other-person]
      (get relationships other-person))
    ([relation other-person]
      (def relationships (assoc relationships other-person relation))
      (if (not (other-person myself)) (other-person relation myself))
      (connect name relation (other-person)))))
