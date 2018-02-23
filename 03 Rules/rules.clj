;; Rule-Based Reasoning
;; Database of Example Rules
;; ========================================
;; Ethan Johnston
;; CS 390L - Cognitive Science and AI
;; Dr. Jody Paul
;; 2 December 2017
;; ========================================
;; Collaborated with:
;;   -Harley Dutton
;;   -Heather DeMarco
;;   -Joseph Brennan
;; ========================================
;; Partially written by: Jody Paul

(ns rules.rules
  (:require [rules.utils :refer :all]
            [clojure.walk :refer :all]
            [clojure.pprint :refer :all]))

(def true-rule
  '(RULE
    (DESCRIPTION "Rule that always fires.")
    (CONDITION (= 42 ( * 7 6)))
    (ACTION (println "I fired! " 42 "is recognized!"))))

(def false-rule
  '(RULE
    (DESCRIPTION "Rule that never fires.")
    (CONDITION (= 99 ( * 7 6)))
    (ACTION (println "Should not have taken any action!"))))

(def fact-adding-rule
  '(RULE
    (DESCRIPTION "Rule that adds a fact to working memory.")
    (CONDITION true)
    (ACTION (do (println "Adding fact to working memory")
                (rules.actions/add-fact! '(DISPLAY (RESOLUTION 1440)))))))

(def fact-retracting-rule
  '(RULE
    (DESCRIPTION "Rule that retracts a fact from working memory.")
    (CONDITION true)
    (ACTION (do (println "Retracting fact from working memory")
                (rules.actions/retract-fact! '(DISPLAY (RESOLUTION 1440)))))))

(def fact-changing-rule
  '(RULE
    (DESCRIPTION "Rule that updates a fact in working memory.")
    (CONDITION (some #(= '(DISPLAY (RESOLUTION 1440)) %) (rules.utils/body rules.actions/*working-memory*)))
    (ACTION (do (println "Updating fact in working memory")
                (rules.actions/retract-fact! '(DISPLAY (RESOLUTION 1440)))
                (rules.actions/add-fact! '(DISPLAY (RESOLUTION 1080)))))))

(def multiple-fact-adding-rule
  '(RULE
    (DESCRIPTION "Rule that adds multiple facts to working memory.")
    (CONDITION true)
    (ACTION (do (println "Adding multiple facts to working memory")
                (rules.actions/add-fact! '(DISPLAY (RESOLUTION 1440)))
                (rules.actions/add-fact! '(DISPLAY (REFRESH 60)))))))

(def multiple-fact-checking-rule
  '(RULE
    (DESCRIPTION "Rule that updates a fact in working memory.")
    (CONDITION (and (some #(= '(DISPLAY (RESOLUTION 1080)) %) (rules.utils/body rules.actions/*working-memory*))
                    (some #(= '(DISPLAY (RESOLUTION 60)) %) (rules.utils/body rules.actions/*working-memory*))))
    (ACTION (do (println "Environment check OK.")
                (rules.actions/add-fact! '(ENVIRONMENT-CHECK OK))))))

(def rule-adding-rule
  '(RULE
    (DESCRIPTION "Rule that adds two rules to working memory.")
    (CONDITION true)
    (ACTION (do (println "Adding rules to rule base")
                (rules.actions/add-rule! rules.rules/fact-adding-rule)
                (rules.actions/add-rule! rules.rules/fact-changing-rule)))))
