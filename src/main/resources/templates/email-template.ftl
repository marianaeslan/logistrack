<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Atualização de Encomenda - Logistrack</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }
        .container { max-width: 600px; margin: 20px auto; background-color: #faf7fd; border-radius: 8px; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header { background-color: #000; color: #ffffff; padding: 20px; text-align: center; }
        .content { padding: 30px; color: #333333; line-height: 1.6; }
        .status-box { background-color: #e7d7fc; border-left: 5px solid #6C06B5; padding: 15px; margin: 20px 0; color: #FDF9FF; border-radius: 8px}
        .info-item { margin-bottom: 10px; }
        .label { font-weight: bold; color: #555; }
        .button { display: inline-block; background-color: #6c06b5; color: #ffffff !important; text-decoration: none !important; padding: 12px 25px; border-radius: 5px; margin-top: 20px; font-weight: bold; font-size: 14px}
        .footer { background-color: #eee; text-align: center; padding: 15px; font-size: 12px; color: #777; }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Logistrack</h1>
    </div>
    <div class="content">
        <h2>Olá, ${nomeCliente}!</h2>
        <p>Temos uma novidade sobre o seu pedido ${nomeLoja}</p>

        <div class="status-box">
            <p class="label">Novo Status:</p>
            <h3 style="margin: 0; color: #2c3e50;">${novoStatus}</h3>
        </div>

        <div class="info-item">
            <span class="label">Código de Rastreio:</span> ${codigoRastreio}
        </div>

        <#if localizacaoAtual??>
            <div class="info-item">
                <span class="label">Localização Atual:</span> ${localizacaoAtual}
            </div>
        </#if>

        <div class="info-item">
            <span class="label">Previsão de Entrega:</span> ${dataPrevisaoEntrega}
        </div>

        <p>Clique no botão abaixo para ver mais detalhes:</p>

        <center>
            <a href="${linkRastreio}" class="button">Rastrear Agora</a>
        </center>
    </div>
    <div class="footer">
        <p>&copy; 2026 Logistrack. Todos os direitos reservados.</p>
        <p>Enviado para: ${emailCliente}</p>
    </div>
</div>
</body>
</html>