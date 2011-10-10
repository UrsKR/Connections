(ns collections.Collections_Test
  (:use clojure.test)
  (:use People))


(defn forget [test] (forget-everyone) (test))

(defn populate [test]
  (def Urs (person "Urs"))
  (def Sandra (person "Sandra"))
  (def Georg (person "Georg"))
  (def Heiner (person "Heiner"))
  (test))

(deftest everyoneKnowsHimself
  (is (true? (knows? Urs Urs))))

(deftest peopleKnowPeopleTheyAreIntroducedTo
  (introduce Urs Sandra)
  (is (true? (knows? Urs Sandra))))

(deftest introductionsMatter
  (is (false? (knows? Urs Sandra))))

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

(deftest youAreLinkedByIntroductions
  (introduce Urs Sandra)
  (is (true? (is-linked-to? Urs Sandra))))

(deftest peopleYouKnowLinkYouToOtherPeople
  (introduce Urs Sandra)
  (introduce Sandra Georg)
  (is (true? (is-linked-to? Urs Georg))))

(deftest someDeadEndsDontHinderALink
  (introduce Urs Sandra)
  (introduce Sandra Heiner)
  (introduce Sandra Georg)
  (is (true? (is-linked-to? Urs Georg))))

(deftest linksGoALongWay
  (introduce Urs Sandra)
  (introduce Sandra Heiner)
  (introduce Heiner Georg)
  (is (true? (is-linked-to? Urs Georg))))

(deftest somePeopleCannotBeLinked
  (introduce Urs Sandra)
  (is (false? (is-linked-to? Urs Georg))))

(deftest recognizesEvenCircularLinks
  (introduce Urs Sandra)
  (introduce Sandra Georg)
  (introduce Georg Urs)
  (is (false? (is-linked-to? Urs Heiner))))

(use-fixtures :each forget populate)
(run-tests)