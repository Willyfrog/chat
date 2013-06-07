(ns chat.models.db
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [chat.models.schema :as schema]))

(defdb db schema/db-spec)

(defentity users)

(defn create-user [user]
  (insert users
          (values user)))

(defn update-user [id first-name last-name email]
  (update users
  (set-fields {:first_name first-name
               :last_name last-name
               :email email})
  (where {:id id})))

(defn get-user [id]
  (first (select users
                 (where {:id id})
                 (limit 1))))

;; lounge

(defentity lounge)

(defn create-message
  [user message]
  (insert lounge
          (values {:user_id user :date (new java.util.Date) :message message})))

(defn retrieve-all-messages
  []
  (select lounge))

(defn retrieve-messages-since [time_since]
  (select lounge (:where {:date [<= time_since]})))
