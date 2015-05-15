# BmvMQ
BmvMQ stands for Bolsa Mexicana de Valores(Mexican Stock Exchange) MQ. It is a MOM-MQ wrapper that allows doing basic publish/subscribe and send/receive operations avoiding vendor lock-in

## Why should I use this MQ wrapper?
Tha main goal of this project is to provide you with every operation needed to send and receive from a Queue or to publish and receive from a Topic in JMS.

You may be asking yourself Why do I want this if there's spring-jms, for instance? Indeed spring-jms is out there and you can perform much more jms operations than this wrapper.
There are three main reasons for doing this. The first one is to abstract some of the core concepts of JMS, making them simple and clear enough to use.

Secondly because you may use Spring within your project. Depending on how often you upgrade your dependencies you may have some conflicts with newer versions that you don't want to address right now, although you should do it on a regular basis. So, avoiding conflicts between Spring versions is another reason you may want to use this wrapper.

Thirdly, as this is a wrapper you may use it on different components of your system and standarizing some messaging features or responsibilities, for instance to be fault taulerant in every single component. Thus you won't have to review every component so you don't repeat yourself.
