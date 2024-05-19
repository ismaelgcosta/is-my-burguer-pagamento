Feature: Efetuar Pagamento

  Scenario: Efetuar pagamento com sucesso
    Given um pagamento válido
    When o pagamento é efetuado
    Then o pagamento deve ser efetuado com sucesso
    And o status do pagamento deve ser PAGO
    And o QR Code deve ser gerado
    And o pagamento deve ser validado