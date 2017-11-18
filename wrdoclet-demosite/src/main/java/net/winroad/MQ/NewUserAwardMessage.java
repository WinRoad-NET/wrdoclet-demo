package net.winroad.MQ;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户注册获得奖励消息
 * @tag mqtest, award
 * @mqConsumer MQ-MSG-TOPICS-TEST 
 * @author Ada
 */
@Getter
@Setter
public class NewUserAwardMessage implements Serializable{
	/**
	 * 用户获得的奖励
	 */
	private String award;
}
