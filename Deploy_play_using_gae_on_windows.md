# Deploy play using gae on windows

After installing gae and siena and updating the environment variables

## To create the .war

---

open cmd -> navigate to the location of the sheelmaayaserver

play war sheelmaayaserver -o sheelmaayaserver.war

-This will create another folder named sheelmaayaserver.war in the same -   place.
- open this folder -> WEB-INF -> appengine-web.xml
- Make sure the value of the application tag is sheelmaaayaa


---


## To deploy

---

open cmd ->
play run sheelmaayaserver

open another cmd -> navigate to the bin folder inside the appengine folder

appcfg.cmd update "c:\Users\ (navigate to )\Sheelmaayaaserver.war"

- it will ask you about your email, enter your gmail
- it will ask you about your password, this is your gmail password, however when you  write it will seem as if you don't write anything however it does. so write your password and press enter it will deploy. Take care that the password must be entered correctly from the first time. if you wrote it wrong, press enter, re-enter your email, and then re-enter your password.


---
