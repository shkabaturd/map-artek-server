(ns map-artek-server.database
  (:require
     [clojure.java.jdbc :as j]
     [clj-time.core :as t]
     [clj-time.local :as l]
     [clj-time.coerce :as c]
     [clj-time.jdbc]))


(def testdata
  {:name "Хрустальный"
   :ping "5.33"
   :time (l/local-now)
   })



(def db
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname "db/database.db"
   })

(defn push-pings-db
  "Запись массива nodes состоящего из node {:ping ping :ip ip :name name} в базу"
  [nodes]
  (j/insert-multi! db :nodes nodes))

(defn get-pings-for-node
  [name]
  (j/query db ["select * from nodes where name =?" name]))

(defn create-db []
  (try (j/db-do-commands db
                       (j/create-table-ddl :nodes
                                         [[:name :text]
                                          [:ping :real]
                                          [:time :text]]
                                          ))
    (catch Exception e (println e))))

(get-pings-for-node "Хрустальный")