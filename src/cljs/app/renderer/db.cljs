(ns app.renderer.db
  (:require
   [datascript.core :as datascript]
   [re-posh.core :as re-posh]))

(def initial-db
  {:application/name "HER-LSF"})

(def initial-ds
  [{:db/id            -1
    :app/type         :type/task
    :task/title       "Learn Clojure a little bit"
    :task/description "Just learn it"
    :task/done?       false}
   {:db/id            -2
    :app/type         :type/task
    :task/title       "Have a coffe"
    :task/description "Just relax"
    :task/done?       false}])

(defn read-db [db]
  (.log js/console "Tried to read db")
  (cljs.reader/read-string db))

(defn connect-db!
  [db]
  (let [conn (datascript/conn-from-db (read-db db))]
    (re-posh/connect! conn)))

(def conn (datascript/create-conn))
(re-posh/connect! conn)
