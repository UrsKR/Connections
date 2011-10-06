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
    (if (not (= nil (connection1 query))) connection1 connection2))

(defn common-attitude-towards-link?
    [connection1 connection2 query1 query2]
    (def connectionForQuery1 (belongsTo connection1 connection2 query1))
    (def connectionForQuery2 (belongsTo connection1 connection2 query2))
    (def link (connectionForQuery1 query1))

    (def attitude1-option-1 (connection1 query1 link))
    (def attitude1-option-2 (connection1 query2 link))
    (def attitude1 (if (= "None" attitude1-option-1) attitude1-option-2 attitude1-option-1))

    (def attitude2-option-1 (connection2 query1 link))
    (def attitude2-option-2 (connection2 query2 link))
    (def attitude2 (if (= "None" attitude2-option-1) attitude2-option-2 attitude2-option-1))

    (= attitude1 attitude2))

(defn are-linked?
    [connection1 connection2 query1 query2]
    (or (= (connection1 query1) (connection2 query2))))

(defn connected
    ([person1 person2 relation query1 query2]
        (let [connectedPersons (list person1 person2)]
            (if (not (list-contains? connectedPersons query1 query2))
                "None"
                (if (= relation "Friendly")
                    "Cooperative"
                    (if (= relation "Antagonistic")
                        "Uncooperative")))))
    ([connection1 connection2 query1 query2]
        (if (not (are-linked? connection1 connection2 query1 query2)) "None"
            (if (common-attitude-towards-link? connection1 connection2 query1 query2) "Cooperative" "Uncooperative"))))

(defn- connect [name1 name2 relation]
    (fn
        ([query] (if (= query name1) name2 (if (= query name2) name1)))
        ([query1 query2] (connected name1 name2 relation query1 query2))))

(defn befriend [name1 name2]
    (connect name1 name2 "Friendly"))

(defn oppose [name1 name2]
    (connect name1 name2 "Antagonistic"))
