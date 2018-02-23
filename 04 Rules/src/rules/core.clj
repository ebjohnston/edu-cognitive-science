;; Rule-Based Reasoning
;; Functionality Testing - Main Class
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
;; RULE
;; A rule is a frame with RULE as its header,
;;   a CONDITION slot, an ACTION slot,
;;   and an optional DESCRIPTION slot.
;; The filler of CONDITION is a predicate function.
;; The filler of ACTION is a function.
;; The filler of DESCRIPTION is a string.
;; ========================================
;; WORKING MEMORY
;; Working memory is a collection of frames
;;   that represent known facts.
;; ========================================
(ns rules.core
  (:gen-class)
  (:require [clojure.pprint :refer :all]
            [rules.actions :refer :all]
            [rules.rules :refer :all]
            [rules.utils :refer :all]))

(defn -main
  "Outputs tests for actions.clj demonstrating use of rule base and working memory"
  []

  (println "")
  (println "=====================================")
  (println "    Rules-Based Programming Tests    ")
  (println "=====================================")
  (println "")

  (println "current rule base:")
  (pprint *rule-base*)
  (println "")

  (println "current working memory:")
  (pprint *working-memory*)
  (println "")

  (println "-------------------------------------")
  (println "adding rule-adding-rule")
  (println "-------------------------------------")
  (println "")

  (println "rule-adding-rule:")
  (pprint rules.rules/rule-adding-rule)
  (rules.actions/add-rule! rules.rules/rule-adding-rule)
  (println "")

  (println "current rule base:")
  (pprint *rule-base*)
  (println "")

  (println "-------------------------------------")
  (println "activating rule-adding-rule")
  (println "-------------------------------------")
  (println "")

  (rules.actions/activate-rule rules.rules/rule-adding-rule)
  (println "")

  (println "current rule base:")
  (pprint *rule-base*)
  (println "")

  (println "-------------------------------------")
  (println "retracting rule-adding-rule")
  (println "-------------------------------------")
  (println "")

  (rules.actions/retract-rule! rules.rules/rule-adding-rule)

  (println "current rule base:")
  (pprint *rule-base*)
  (println "")

  (println "-------------------------------------")
  (println "activating fact-adding-rule")
  (println "-------------------------------------")
  (println "")

  (rules.actions/activate-rule rules.rules/fact-adding-rule)
  (println "")

  (println "current working memory:")
  (pprint *working-memory*)
  (println "")

  (println "-------------------------------------")
  (println "activating fact-changing-rule")
  (println "-------------------------------------")
  (println "")

  (rules.actions/activate-rule rules.rules/fact-changing-rule)
  (println "")

  (println "current working memory:")
  (pprint *working-memory*)
  (println "")
)
