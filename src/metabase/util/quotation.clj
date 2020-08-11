(ns metabase.util.quotation)


(def ^:const ^:private quotations
  [{:quote "To transform aid and philanthropy to accelerate community-led change."
    :author "GlobalGiving's Mission"
   }
  ])


(defn random-quote
  "Get a randomized quotation about working with data."
  []
  (rand-nth quotations))
