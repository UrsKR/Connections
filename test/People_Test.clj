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

(deftest introductionsCanInvolveSomePeople
  (introduce Urs Sandra Georg)
  (is (true? (knows? Urs Georg))))

(deftest peopleRememberEvenAfterMoreIntroductions
  (introduce Urs Sandra)
  (introduce Urs Georg)
  (is (true? (knows? Urs Sandra))))

(deftest mutualIntroductionsAreMutuallyRemembered
  (introduce Urs Sandra)
  (introduce Sandra Urs)
  (is (true? (know-each-other? Urs Sandra))))

(deftest peopleYouKnowLinkYouToOtherPeople
  (introduce Urs Sandra)
  (introduce Sandra Georg)
  (comment is (true? (is-linked-to? Urs Georg))))

(run-tests)