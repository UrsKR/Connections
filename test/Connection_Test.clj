(ns connections.test
  (:use clojure.test)
  (:use Connections))

(defn create-relationships [test]
  ((do
     (def Urs-mag-Hunde (befriend "Urs" "Hunde"))
     (def Urs-mag-keine-Stinker (oppose "Urs" "Stinker"))
     (def Hunde-moegen-Herrchen (befriend "Hunde" "Hundebesitzer"))
     (def Hunde-moegen-keine-Stinker (oppose "Hunde" "Stinker"))
     (def Stinker-moegen-keine-Katzen (oppose "Stinker" "Katzen")))
    (test)))

(deftest friendsCooperate
  (is (= "Cooperative" (connected "Urs" "Hunde" Urs-mag-Hunde))))

(deftest enemiesHinder
  (is (= "Uncooperative" (connected "Urs" "Stinker" Urs-mag-keine-Stinker))))

(deftest ignoranceYieldsNoPrevalentBehaviour
  (is (= "None" (connected "Urs" "Stinker" Urs-mag-Hunde))))

(deftest relationshipsAreCommutative
  (is (= "Cooperative" (connected "Hunde" "Urs" Urs-mag-Hunde))))


(deftest relationshipsAreVisible
  (is (= "Hunde" (Urs-mag-Hunde "Urs"))))

(deftest relationshipsAreVisibleInBothDirections
  (is (= "Urs" (Urs-mag-Hunde "Hunde"))))

(deftest relationshipsConcernOnlyTwoPersons
  (is (= nil (Urs-mag-Hunde "Stinker"))))


(deftest friendsOfMyFriendsAreMyFriends
  (is (= "Cooperative" (connected "Urs" "Hundebesitzer" Urs-mag-Hunde Hunde-moegen-Herrchen))))

(deftest friendsOfMyFriendsAreMyFriendsNoMatterWhichWayILookAtIt
  (is (= "Cooperative" (connected "Hundebesitzer" "Urs" Urs-mag-Hunde Hunde-moegen-Herrchen))))

(deftest enemiesOfMyFriendsAreMyEnemies
  (is (= "Uncooperative" (connected "Urs" "Stinker" Urs-mag-Hunde Hunde-moegen-keine-Stinker))))

(deftest enemiesOfMyFriendsAreMyEnemiesNoMatterWhichWayILookAtIt
  (is (= "Uncooperative" (connected "Stinker" "Urs" Urs-mag-Hunde Hunde-moegen-keine-Stinker))))

(deftest aCommonEnemyUnites
  (is (= "Cooperative" (connected "Urs" "Katzen" Urs-mag-keine-Stinker Stinker-moegen-keine-Katzen))))

(deftest unrelatedRelationshipsDoNotMatter
  (is (= "None" (connected "Urs" "Stinker" Urs-mag-Hunde Stinker-moegen-keine-Katzen))))

(use-fixtures :each create-relationships)
(run-tests)