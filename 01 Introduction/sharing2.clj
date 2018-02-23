;; Program 01 - Sharing
;; Ethan Johnston
;; Cognitive Science and Artificial Intelligence - CS390L
;; Dr. Jody Paul - Fall 2017

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
