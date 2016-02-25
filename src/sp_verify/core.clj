(ns sp-verify.core
  (:gen-class))

(defn verify-line [[line-number line]]
  (let [row-cells (.split line ",")]
    (let [invoice-no (nth row-cells 1)
          total-income (nth row-cells 5)
          before-vat (nth row-cells 6)]
         {:result "good", :line-number line-number, :invoice-no invoice-no})
    ))

(defn with-index [lines]
  (map-indexed  (fn [idx itm] [idx itm]) lines))

(defn verify-lines [lines]
  (map verify-line (with-index lines)))

(defn read-into-lines [file-name]
  (with-open [rdr (clojure.java.io/reader file-name)]
    (doall (line-seq rdr))))

(defn verify-sp-payment []
  (let [my-calculation-lines (read-into-lines "/Users/wickedwukong/Dropbox/company/SP-Management/Fund-management.csv")
        sp-breakdown-lines (read-into-lines "/Users/wickedwukong/Dropbox/company/SP-Management/invoice-breakdown.csv")]
    (verify-lines (rest my-calculation-lines))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (verify-sp-payment))
