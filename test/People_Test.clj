(ns collections.Collections_Test
  (:use clojure.test)
  (:use People))

(def Urs (person "Urs"))
(def Sandra (person "Sandra"))
(def Georg (person "Georg"))

(deftest peopleKnowPeopleTheyAreIntroducedTo
  (introduce Urs Sandra)
  (is (true? (knows? Urs Sandra))))

(deftest introductionsAreUnidirectional
  (introduce Urs Sandra)
  (is (false? (knows? Sandra Urs))))

(deftest introductionsCanInvolveManyPeople
  (introduce Urs Sandra Georg)
  (is (true? (knows? Urs Georg))))

(deftest mutualIntroductions
  (introduce Urs Sandra)
  (introduce Sandra Urs)
  (is (true? (know-each-other? Urs Sandra))))

(deftest peopleYouKnowLinkYouToOtherPeople
  (introduce Urs Sandra)
  (introduce Sandra Georg)
  (is (true? (is-linked-to? Urs Georg))))

(run-tests)