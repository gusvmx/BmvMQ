# BmvMQ
MOM-MQ wrapper that allows doing basic publish/subscribe and send/receive operations avoiding vendor lock-in

## Why should I use this MQ wrapper?
Tha main goal of this project is to provide you with every operation needed to send and receive from a Queue ro to publish and receive from a Topic in JMS.

You may be asking yourself Why do I want this if there's spring-jms, for instance? Indeed spring-jms is out there and you can perform much more jms operations than this wrapper.
There are two main reasons for doing this. The first one is to abstract some of the core concepts of JMS, making the simple and clear enough to use.

Secondly because you may use Spring within your project. Depending on how often you upgrade your dependencies you may have some conflicts on newer versions that you don't want to address them right now, although you should do it on a regular basis.
