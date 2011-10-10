(ns collections.Collections_Test
  (:use clojure.test)
  (:use People))

(def Urs (person "Urs"))
(def Sandra (person "Sandra"))

(deftest peopleKnowPeopleTheyAreIntroducedTo
  (def Urs (introduce Urs Sandra))
  (is (true? (knows? Urs Sandra))))

(deftest introductionsAreUnidirectional
  (def Urs (introduce Urs Sandra))
  (is (false? (knows? Sandra Urs))))

(run-tests)