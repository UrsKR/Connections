(ns Connections)

(defn list-contains?
    ([collection value]
        (let [sequence (seq collection)]
            (if sequence (some #(= value %) sequence))))
    ([collection value & next]
        (if (list-contains? collection value) (apply list-contains? collection next))))

(defn connected
    ([person1 person2 relation query1 query2]
        (let [connectedPersons (list person1 person2)]
            (if (not (list-contains? connectedPersons query1 query2))
                "None"
                (if (= relation "Friendly") "Cooperative"
                    (if (= relation "Antagonistic") "Uncooperative")))))
    ([person1 person2 relation person3 person4 relation]

        ))

(defn- connect [name1 name2 relation]
    (fn
        ([query] (if (= query name1) name2 (if (= query name2) name1)))
        ([query1 query2] (connected name1 name2 relation query1 query2))))

(defn befriend [name1 name2]
    (connect name1 name2 "Friendly"))

(defn oppose [name1 name2]
    (connect name1 name2 "Antagonistic"))