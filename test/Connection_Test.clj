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
    (is (= "Cooperative" (Urs-mag-Hunde "Urs" "Hunde"))))

(deftest enemiesHinder
    (is (= "Uncooperative" (Urs-mag-keine-Stinker "Urs" "Stinker"))))

(deftest ignoranceYieldsNoPrevalentBehaviour
    (is (= "None" (Urs-mag-Hunde "Urs" "Stinker"))))

(deftest relationshipsAreCommutative
    (is (= "Cooperative" (Urs-mag-Hunde "Hunde" "Urs"))))


(deftest relationshipsAreVisible
    (is (= "Hunde" (Urs-mag-Hunde "Urs"))))

(deftest relationshipsAreVisibleInBothDirections
    (is (= "Urs" (Urs-mag-Hunde "Hunde"))))

(deftest relationshipsConcernOnlyTwoPersons
    (is (= nil (Urs-mag-Hunde "Stinker"))))


(deftest friendsOfMyFriendsAreMyFriends
    (is (= "Cooperative" (connected Urs-mag-Hunde Hunde-moegen-Herrchen "Urs" "Hundebesitzer"))))

(deftest enemiesOfMyFriendsAreMyEnemies
    (is (= "Uncooperative" (connected Urs-mag-Hunde Hunde-moegen-keine-Stinker "Urs" "Stinker"))))

(deftest aCommonEnemyUnites
    (is (= "Cooperative" (connected Urs-mag-keine-Stinker Stinker-moegen-keine-Katzen "Urs" "Katzen"))))

(deftest unrelatedRelationshipsDoNotMatter
    (is (= "None" (connected Urs-mag-Hunde Stinker-moegen-keine-Katzen "Urs" "Stinker"))))

(use-fixtures :each create-relationships)
(run-tests)