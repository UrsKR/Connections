(ns connections.test
    (:use clojure.test)
    (:use Connections))

(deftest friendsCooperate
    (is (= "Cooperative" ((befriend "Urs" "Hunde") "Urs" "Hunde"))))

(deftest enemiesHinder
    (is (= "Uncooperative" ((oppose "Urs" "Stinker") "Urs" "Stinker"))))

(deftest ignoranceYieldsNoPrevalentBehaviour
    (is (= "None" ((befriend "Urs" "Hunde") "Urs" "Stinker"))))

(deftest relationshipsAreCommutative
    (is (= "Cooperative" ((befriend "Urs" "Hunde") "Hunde" "Urs"))))


(deftest relationshipsAreVisible
    (is (= "Hunde" ((befriend "Urs" "Hunde") "Urs"))))

(deftest relationshipsAreVisibleInBothDirections
    (is (= "Urs" ((befriend "Urs" "Hunde") "Hunde"))))

(deftest relationshipsConcernOnlyTwoPersons
    (is (= nil ((befriend "Urs" "Hunde") "Stinker"))))


(deftest friendsOfMyFriendsAreMyFriends
    (is (= "Cooperative" (connected (befriend "Urs" "Hunde") (befriend "Hunde" "Hundebesitzer") "Urs" "Hundebesitzer")))
    )

(deftest enemiesOfMyFriendsAreMyEnemies
    (is (= "Uncooperative" (connected (befriend "Urs" "Hunde") (oppose "Hunde" "Hundebesitzer") "Urs" "Hundebesitzer")))
    )

(deftest aCommonEnemyUnites
    (is (= "Cooperative" (connected (oppose "Urs" "Stinker") (oppose "Stinker" "Hundebesitzer") "Urs" "Hundebesitzer")))
    )

(deftest unrelatedRelationshipsDoNotMatter
    (is (= "None" (connected (befriend "Urs" "Hunde") (oppose "Stinker" "Hundebesitzer") "Urs" "Hundebesitzer")))
    )

(run-tests)