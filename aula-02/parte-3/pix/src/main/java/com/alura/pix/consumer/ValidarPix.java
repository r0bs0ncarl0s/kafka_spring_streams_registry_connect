package com.alura.pix.consumer;

import com.alura.pix.dto.PixDTO;
import com.alura.pix.dto.PixStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class ValidarPix {

    @KafkaListener(topics = "pix-topic-2", groupId = "group-10")
    @RetryableTopic(
            backoff = @Backoff(value = 3000L),
            attempts = "5",
            autoCreateTopics = "true",
            include = RuntimeException.class)
    public void process(PixDTO pixDTO) {
        System.out.println(pixDTO);

        if (pixDTO.getValor() != null && pixDTO.getValor() > 0) {
            pixDTO.setStatus(PixStatus.PROCESSADO);
        } else  {
            pixDTO.setStatus(PixStatus.ERRO);
            throw new RuntimeException();
        }

    }


}
