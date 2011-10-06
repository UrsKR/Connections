(ns Connections
  (:use Collections))

(def likes "likes")
(def like likes)
(def hates "hates")
(def hate hates)

(def cooperative "Cooperative")
(def uncooperative "Uncooperative")
(def ignorant "None")

(defn- belongsTo [query connection1 connection2]
  (if (connection1 query) connection1 connection2))

(defn- common-attitude-towards-link?
  [query1 query2 connection1 connection2]
  (def connectionForQuery1 (belongsTo query1 connection1 connection2))
  (def connectionForQuery2 (belongsTo query2 connection1 connection2))
  (def link (connectionForQuery1 query1))
  (def attitude1 (connectionForQuery1 query1 link))
  (def attitude2 (connectionForQuery2 query2 link))
  (= attitude1 attitude2))

(defn- are-linked?
  ([query1 query2 connection]
    (and (connection query1) (connection query2)))
  ([query1 query2 connection1 connection2]
    (= (connection1 query1) (connection2 query2))))

(defn connected
  ([query1 query2 connection]
    (connection query1 query2))
  ([query1 query2 connection1 connection2]
    (if (not (are-linked? query1 query2 connection1 connection2)) ignorant
      (if (common-attitude-towards-link? query1 query2 connection1 connection2) cooperative uncooperative))))

(defn- connect [name1 relation name2]
  (fn
    ([query]
      (if (= query name1) name2 (if (= query name2) name1)))
    ([query1 query2]
      (let [connectedPersons (list name1 name2)]
        (if (not (list-contains? connectedPersons query1 query2))
          ignorant
          (if (= relation likes)
            cooperative
            (if (= relation hates)
              uncooperative)))))))

(defn befriend [name1 name2]
  (connect name1 likes name2))

(defn oppose [name1 name2]
  (connect name1 hates name2))

(defn person [name]
  (fn
    ([] name)
    ([relation name2]
      (if (= likes relation) (befriend name (name2)) (if (= hates relation) (oppose name (name2)))))))
