(ns Connections)

(defn list-contains?
    ([collection value]
        (let [sequence (seq collection)]
            (if sequence
                (some #(= value %) sequence))))
    ([collection value & next]
        (if (list-contains? collection value)
            (apply list-contains? collection next))))

(defn belongsTo [connection1 connection2 query]
    (if (connection1 query) connection1 connection2))

(defn common-attitude-towards-link?
    [connection1 connection2 query1 query2]
    (def connectionForQuery1 (belongsTo connection1 connection2 query1))
    (def connectionForQuery2 (belongsTo connection1 connection2 query2))
    (def link (connectionForQuery1 query1))
    (def attitude1 (connectionForQuery1 query1 link))
    (def attitude2 (connectionForQuery2 query2 link))
    (= attitude1 attitude2))

(defn are-linked?
    ([connection query1 query2]
        (and (not (= nil (connection query1))) (not (= nil (connection query2)))))
    ([connection1 connection2 query1 query2]
        (= (connection1 query1) (connection2 query2))))

(defn connected
    ([person1 relation person2 query1 query2]
        (let [connectedPersons (list person1 person2)]
            (if (not (list-contains? connectedPersons query1 query2))
                "None"
                (if (= relation "likes")
                    "Cooperative"
                    (if (= relation "hates")
                        "Uncooperative")))))
    ([connection query1 query2]
        (if (not (are-linked? connection query1 query2)) "None"
            (connection query1 query2)))
    ([connection1 connection2 query1 query2]
        (if (not (are-linked? connection1 connection2 query1 query2)) "None"
            (if (common-attitude-towards-link? connection1 connection2 query1 query2) "Cooperative" "Uncooperative"))))

(defn- connect [name1 relation name2]
    (fn
        ([query] (if (= query name1) name2 (if (= query name2) name1)))
        ([query1 query2] (connected name1 relation name2 query1 query2))))

(defn befriend [name1 name2]
    (connect name1 "likes" name2))

(defn oppose [name1 name2]
    (connect name1 "hates" name2))
