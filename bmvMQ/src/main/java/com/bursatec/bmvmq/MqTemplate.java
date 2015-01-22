/**
 * Bursatec - BMV Sep 5, 2014
 * This software is the confidential and proprietary information of 
 * Bursatec and Bolsa Mexicana de Valores("Confidential Information").
 *
 * You shall not disclose such confidential information and shall use
 * it only within a project and/or the offices of Bursatec or Bolsa Mexicana de Valores
 */
package com.bursatec.bmvmq;

import java.io.Serializable;

import com.bursatec.bmvmq.listener.MessageListener;

/**
 * @author Gustavo Vargas
 *
 */
public interface MqTemplate {

	/**
	 * @param destination El nombre del tópico donde se publicará el mensaje.
	 * @param message El objeto serializable que se publicará.
	 */
	void publish(String destination, Serializable message);
	
	/**
	 * @param destination El nombre del tópico donde se publicará el mensaje.
	 * @param message El mensaje a publicar.
	 */
	void publish(String destination, String message);
	
	/**
	 * @param destination El nombre del tópico donde se publicará el mensaje.
	 * @param message El mensaje en su representación de arreglo de bytes a publicar.
	 */
	void publish(String destination, byte[] message);
	
	/**
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El objeto serializable que se enviará.
	 */
	void send(String destination, Serializable message);
	/**
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El objeto serializable que se enviará.
	 * @param messageGroup Identificador del grupo al que pertenece el mensaje.
	 */
	void send(String destination, Serializable message, String messageGroup);
	
	/**
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El mensaje a enviar.
	 */
	void send(String destination, String message);
	/**
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El mensaje a enviar.
	 * @param messageGroup Identificador del grupo al que pertenece el mensaje.
	 */
	void send(String destination, String message, String messageGroup);
	
	/**
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El mensaje en su representación de arreglo de bytes a enviar.
	 */
	void send(String destination, byte[] message);
	/**
	 * @param destination El nombre de la cola donde se enviará el mensaje.
	 * @param message El mensaje en su representación de arreglo de bytes a enviar.
	 * @param messageGroup Identificador del grupo al que pertenece el mensaje.
	 */
	void send(String destination, byte[] message, String messageGroup);
	
	/**
	 * @param destination El nombre de la cola de donde se recibirán mensajes.
	 * @param messageListener El callback donde se entregarán los mensajes recibidos.
	 */
	void receive(String destination, MessageListener messageListener);
	/**
	 * @param destination El nombre de la cola de donde se recibirán mensajes.
	 * @param messageListener El callback donde se entregarán los mensajes recibidos.
	 */
	void receiveExclusively(String destination, MessageListener messageListener);
	
	/**
	 * @param destination El nombre del tópico al que se realizará la suscripción.
	 * @param messageListener El callback donde se entregarán los mensajes recibidos en la suscripción.
	 */
	void subscribe(String destination, MessageListener messageListener);
	
	/**
	 * Se suscribe al tópico indicado de manera durable. Esto significa que el
	 * cliente se registra en el broker JMS con un único identificador. Esto
	 * hace que el broker almacene todos los mensajes que pudieran llegar
	 * mientras el suscriptor este fuera de línea. En cuanto el suscriptor
	 * vuelva a estar en línea, todos los mensajes que fluyeron en el tópico les
	 * serán entregados.
	 * 
	 * 
	 * Las suscripciones durables cuentan con las siguientes limitaciones. Un
	 * consumidor se crea con un único clientId y nombre de suscripción durable.
	 * Para cumplir con la especificación JMS, sólo una conexión puede estar
	 * activa a la vez para un clientId y sólo un consumidor puede estar activo
	 * para un clientId y nombre de suscripción. En otras palabras, sólo un hilo
	 * puede estar activamente consumiendo de un tópico.
	 * 
	 * 
	 * Como resultado, 2 consumidores no pueden suscribirse a la misma
	 * suscripción durable. Tener multiples consumidores para balanceo de carga,
	 * multi-threading o disponibilidad no está permitido por la especificación
	 * JMS.
	 * 
	 * 
	 * *Reemplazar suscripciones durables con queues y tópicos virtuales* Un
	 * tópico virtual es creado y mapeado hacia uno o más queues. Esto permite
	 * el balanceo de carga para los mensajes provenientes de ese tópico. Además
	 * ofrece alta disponibilidad, si los consumidores se caen, los mensajes
	 * publicados hacia el tópico están disponibles debido a la naturaleza
	 * persistente del queue, por lo tanto, los mensajes no se pierden.
	 * 
	 * @param destination
	 *            El nombre del tópico al que se realizará la suscripción
	 *            durable.
	 * @param durableSubscriptionName
	 *            El nombre de la suscripción durable.
	 * @param messageListener
	 *            El callback donde se entregarán los mensajes recibidos en la
	 *            suscripción.
	 */
	void durableSubscription(String destination, String durableSubscriptionName, 
			MessageListener messageListener);
	
	/**
	 * @param destination El nombre del queue del que se dejará de recibir mensajes.
	 */
	void stopReceiving(String destination);
	
	/**
	 * @param destination El nombre del topico del que se desuscribirá para dejar de recibir mensajes.
	 */
	void unsubscribe(String destination);

}
