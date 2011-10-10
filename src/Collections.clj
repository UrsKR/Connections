(ns Collections)

(defn list-contains?
  ([collection value]
    (let [sequence (seq collection)]
      (if sequence
        (some #(= value %) sequence))))
  ([collection value & next]
    (if (list-contains? collection value)
      (apply list-contains? collection next))))