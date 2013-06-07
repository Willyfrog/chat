(ns chat.models.schema
  (:require [clojure.java.jdbc :as sql]
            [noir.io :as io]))

(def db-store "chat.db")

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2"
              :subname (str (io/resource-path) db-store)
              :user "sa"
              :password ""
              :naming {:keys clojure.string/lower-case
                       :fields clojure.string/upper-case}})
(defn initialized?
  "checks to see if the database schema is present"
  []
  (.exists (new java.io.File (str (io/resource-path) db-store ".h2.db"))))

(defn create-users-table
  []
  (sql/with-connection db-spec
    (sql/create-table
      :users
      [:id "varchar(20) PRIMARY KEY"]
      [:first_name "varchar(30)"]
      [:last_name "varchar(30)"]
      [:email "varchar(30) NOT NULL"]
      [:admin :boolean]
      [:last_login :time]
      [:is_active :boolean]
      [:pass "varchar(100)"])))

(defn create-lounge-table
  []
  (sql/with-connection db-spec
    (sql/create-table
     :lounge
     [:id :serial "PRIMARY KEY"]
     [:user_id "varchar(20) references users (id)"]
     [:date :datetime]
     [:message :text])))

(defn create-tables
  "creates the database tables used by the application"
  []
  (create-users-table)
  (create-lounge-table)
  )
