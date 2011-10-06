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

(deftest friendlyBehaviourIsTransitive
    (is (= "Cooperative" (connected (befriend "Urs" "Hunde") (befriend "Hunde" "Hundebesitzer") "Urs" "Hundebesitzer")))
    )

(run-tests)