(ns connections.test
  (:use clojure.test)
  (:use Connections))

(defn create-relationships [test]
  ((do
     (def Urs (person "Urs"))
     (def dogs (person "Dogs"))
     (def stinkers (person "Stinkers"))
     (def dog-owners (person "Dog owners"))
     (def cats (person "Cats"))
     (def Urs-likes-dogs (Urs likes dogs))
     (def Urs-hates-stinkers (Urs hates stinkers))
     (def dogs-like-owners (dogs like dog-owners))
     (def dogs-hate-stinkers (dogs hate stinkers))
     (def stinkers-hate-cats (stinkers hate cats)))
    (test)))

(deftest friendsCooperate
  (is (= "Cooperative" ((connected "Urs" "Dogs" Urs-likes-dogs)))))

(deftest enemiesHinder
  (is (= "Uncooperative" ((connected "Urs" "Stinkers" Urs-hates-stinkers)))))

(deftest ignoranceYieldsNoPrevalentBehaviour
  (is (= "None" ((connected "Urs" "Stinkers" Urs-likes-dogs)))))

(deftest relationshipsAreCommutative
  (is (= "Cooperative" ((connected "Dogs" "Urs" Urs-likes-dogs)))))


(deftest relationshipsAreVisible
  (is (= "Dogs" (Urs-likes-dogs "Urs"))))

(deftest relationshipsAreVisibleInBothDirections
  (is (= "Urs" (Urs-likes-dogs "Dogs"))))

(deftest relationshipsConcernOnlyTwoPersons
  (is (= nil (Urs-likes-dogs "Stinkers"))))


(deftest friendsOfMyFriendsAreMyFriends
  (is (= "Cooperative" (connected "Urs" "Dog owners" Urs-likes-dogs dogs-like-owners))))

(deftest friendsOfMyFriendsAreMyFriendsNoMatterWhichWayILookAtIt
  (is (= "Cooperative" (connected "Dog owners" "Urs" Urs-likes-dogs dogs-like-owners))))

(deftest enemiesOfMyFriendsAreMyEnemies
  (is (= "Uncooperative" (connected "Urs" "Stinkers" Urs-likes-dogs dogs-hate-stinkers))))

(deftest enemiesOfMyFriendsAreMyEnemiesNoMatterWhichWayILookAtIt
  (is (= "Uncooperative" (connected "Stinkers" "Urs" Urs-likes-dogs dogs-hate-stinkers))))

(deftest aCommonEnemyUnites
  (is (= "Cooperative" (connected "Urs" "Cats" Urs-hates-stinkers stinkers-hate-cats))))

(deftest unrelatedRelationshipsDoNotMatter
  (is (= "None" (connected "Urs" "Stinkers" Urs-likes-dogs stinkers-hate-cats))))

(use-fixtures :each create-relationships)
(run-tests)