;; Introductory Clojure Program
;; ========================================
;; Ethan Johnstdon
;; Cs 390L - Cognitive Science and AI
;; Dr. Jody Paul
;; 3 September 2017

(defn print-response [i]
  (println
    (case i
      0 "Hi"
      1 "Hello"
      2 "Howdy"
      3 "Greetings"
      4 "Hey"
      5 "G'day"
      6 "Good day"
      7 "How are you"
      8 "What's up"
      9 "How goes it"
      10 "How do you do"
      11 "Hi there"
    )
  )
)

(run! print-response (shuffle (range 12)))
