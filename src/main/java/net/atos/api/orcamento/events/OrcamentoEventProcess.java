package net.atos.api.orcamento.events;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import net.atos.api.orcamento.domain.OrcamentoVO;



@Service
public class OrcamentoEventProcess {
	
	
	private RabbitTemplate rabbitTemplate;

	public OrcamentoEventProcess(RabbitTemplate pRabbitTemplate) {
		this.rabbitTemplate = pRabbitTemplate;
		
	}
	

	@Async
	@TransactionalEventListener
	public void handleEvent(OrcamentoCreatedEvent event) {
		
		OrcamentoVO orcamento = event.getOrcamento();
		
		this.rabbitTemplate.convertAndSend("orcamento", 
				"orc.created.pedido", orcamento);
	}
	
	

}
