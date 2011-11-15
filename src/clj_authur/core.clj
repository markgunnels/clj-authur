(ns clj-authur.core
  (:require [clj-http.client :as http])
  (:use [clojure.data.json :only (json-str
                                  write-json
                                  read-json)]))

(def authur-base-url "http://authur.wilbur.io/")

;;Register Application
;;curl -XPUT http:// [admin]: [pass]@authur.wilbur.io/applications -d '{ "name": "app1"}'
;;#=> { "success": "true"}

(defn register-application
  [admin-uname admin-passwd application-name]
  (let [register-application-url (str authur-base-url
                                      "applications/"
                                      application-name)]
    (http/put register-application-url
              {:basic-auth [admin-uname admin-passwd]
               :content-type :json
               :accept :json
               :body (json-str {:name application-name})})))

;;Register User
;;curl -XPUT http:// [admin]: [pass]@authur.wilbur.io/users -d '{ "username": "foo", "password": "bar", "confirm": "bar"} '
;;#=> { "success": "true"}

(defn register-user
  [admin-uname admin-passwd uname passwd confirm]
  (let [register-user-url (str authur-base-url
                               "users/"
                               uname)]
    (http/put register-user-url
              {:basic-auth [admin-uname admin-passwd]
               :content-type :json
               :accept :json
               :body (json-str {:username uname
                                :password passwd
                                :confirm confirm})})))

;;Attach User to Application
;;curl -XPOST http:// [admin]: [pass]@authur.wilbur.io/users/ [user]/apps/ [app]
;;#=> { "success": "true"}

(defn attach-user-to-application
  [admin-uname admin-passwd uname app]
  (let [register-user-url (str authur-base-url
                               "users/"
                               uname
                               "/apps/"
                               app)]
    (http/post register-user-url
               {:basic-auth [admin-uname admin-passwd]})))

;;Authenticate User
;;curl -XPOST http:// [admin]: [pass]@authur.wilbur.io/auth/ [app] -d ' {"username": "foo", password ": "bar "}'"}

(defn authenticate-user
  [admin-uname admin-passwd app uname passwd]
  (let [register-user-url (str authur-base-url
                               "auth/"
                               app)
        result (http/post register-user-url
                          {:basic-auth [admin-uname admin-passwd]
                           :content-type :json
                           :accept :json
                           :body (json-str {:username uname
                                            :password passwd})})
        body (read-json (:body result))]
    (:success body)))
