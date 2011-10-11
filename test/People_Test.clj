(ns collections.Collections_Test
  (:use clojure.test)
  (:use People))


(defn populate [test]
  (def Urs (person "Urs"))
  (def Sandra (person "Sandra"))
  (def Georg (person "Georg"))
  (def Heiner (person "Heiner"))
  (test))

(deftest everyoneKnowsHimself
  (is (true? (knows? Urs Urs))))

(deftest peopleKnowPeopleTheyAreIntroducedTo
  (def Urs (introduce Urs Sandra))
  (is (true? (knows? Urs Sandra))))

(deftest introductionsMatter
  (is (false? (knows? Urs Sandra))))

(deftest introductionsAreUnidirectional
  (def Urs (introduce Urs Sandra))
  (is (false? (knows? Sandra Urs))))

(deftest introductionsCanInvolveSomePeople
  (def Urs (introduce Urs Sandra Georg))
  (is (true? (knows? Urs Georg))))

(deftest peopleRememberEvenAfterMoreIntroductions
  (def Urs (introduce Urs Sandra))
  (def Urs (introduce Urs Georg))
  (is (true? (knows? Urs Sandra))))

(deftest mutualIntroductionsAreMutuallyRemembered
  (def Urs (introduce Urs Sandra))
  (def Sandra (introduce Sandra Urs))
  (is (true? (know-each-other? Urs Sandra))))

(deftest youAreLinkedByIntroductions
  (def Urs (introduce Urs Sandra))
  (is (true? (is-linked-to? Urs Sandra))))

(deftest peopleYouKnowLinkYouToOtherPeople
  (def Sandra (introduce Sandra Georg))
  (def Urs (introduce Urs Sandra))
  (is (true? (is-linked-to? Urs Georg))))

(deftest someDeadEndsDontHinderALink
  (def Sandra (introduce Sandra Heiner))
  (def Sandra (introduce Sandra Georg))
  (def Urs (introduce Urs Sandra))
  (is (true? (is-linked-to? Urs Georg))))

(deftest linksGoALongWay
  (def Heiner (introduce Heiner Georg))
  (def Sandra (introduce Sandra Heiner))
  (def Urs (introduce Urs Sandra))
  (is (true? (is-linked-to? Urs Georg))))

(deftest somePeopleCannotBeLinked
  (def Urs (introduce Urs Sandra))
  (is (false? (is-linked-to? Urs Georg))))

(deftest recognizesEvenCircularLinks
  (def Urs (introduce Urs Sandra))
  (def Sandra (introduce Sandra Georg))
  (def Georg (introduce Georg Urs))
  (is (false? (is-linked-to? Urs Heiner))))

(use-fixtures :each populate)
(run-tests)