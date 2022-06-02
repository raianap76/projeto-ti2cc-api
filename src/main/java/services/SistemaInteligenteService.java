package services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;

import response.StandardResponse;
import response.StatusResponse;

/*
Esse código é responsável por enviar tempo de experiencia de uma pessoa  e obter de volta a classe prevista (pode ser contratado, não pode). 
O serviço que receberá esses dados é o que foi usado o serviço web do nosso modelo na Azure.
Funciona em 4 passos principais:
1) Constrói o objeto da chamada HTTP a ser enviado ao serviço web do modelo
2) Coloca como o `body` da chamada HTTP as características de áudio que são classificados
3) Envia a requisição ao serviço
4) Recebe a resposta em JSON e a converte para uma List de HashMaps. O retorno do modelo
   contém as características de cada áudio enviadas, a probabilidade de cada set de características
   pertencer a uma classe e a classe prevista.
*/

public class SistemaInteligenteService {
    // Endpoint do modelo. Para mais informações, ver o seguinte
    // O endpoint está presente no campo "REST Endpoint" no serviço web do modelo.
    private static final String MODEL_URL = "http://4cb2951f-db83-49cf-a38a-9b416396193b.eastus.azurecontainer.io/score";
    
    // Chave de API do seu serviço na Azure
    // A chave está presente no campo "Primary Key" no serviço web do modelo.
    private static final String API_KEY = "9btxfbn2ZtBEwxE4fRsmBmXcBlp4EkSG";

    public String treinaModelo() {
        // Construímos a nosso objeto HTTP que será enviado ao servidor do modelo. 
        // O `API_KEY` é utilizado nos headers e os dados enviados são atribuídos ao objeto
        // na linha 43 por meio da função `.sampleData`
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(MODEL_URL))
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(sampleData()))
                .build();

        try {
            // Realiza-se a chamada HTTP para o servidor do modelo. O objeto `client` definido na linha 40
            // chama o método `#send` passando o request da linha 41, que é quem contém as informações da URL, 
            // autenticação com a API_KEY e os dados a serem classificados.
            HttpResponse<String> response  = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            // Convertemos a reposta para uma List de objetos de HashMap. Nas linhas 86-111 há um exemplo de retorno
            // da função `.responseMapBody`.
            List<Map<String, Object>> classification = responseMapBody(response.body());
            System.out.println(classification);
            
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(response.body())));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
					new Gson().toJson("Erro ao treinar modelo API fora do ar!")));
        }
    }

    private static String sampleData() {
        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("class", "");
        item.put("valence", 3.9394);
        item.put("energy", 1);
        item.put("liveness", 1);
        item.put("speechiness", 1);
        item.put("instrumentalness", 1);
        item.put("tempo", 150);
        item.put("danceability", 8.393);
        item.put("acousticness", 0.992);

        array.add(item);

        return array.toString();
    }

    /*
    
    Cada HashMap contém as características de contratação de uma pessoa que foram enviadas para a classificação (ser contratado,
    não ser, boa permanencia na empresa, quantas vezes mudou de area a probabilidade
    delas pertencerem a cada uma das classes (Scored Probabilities_sercontatado, Scored Probabilities_naosercontatado,
    Scored Probabilities_permanecerempresa,) e, por fim, a classe prevista (Scored Labels),
    que nada mais é a classe com a maior probabilidade das características pertencerem
    */
    private static List<Map<String, Object>> responseMapBody(String body) {
        Map<String, Object> hm;
        List<Map<String, Object>> res = new ArrayList<>();

        // Parseia a resposta em string para JSON
        Object obj = JSONValue.parse(body);
        JSONObject jsonObject = (JSONObject) obj;
        
        // O retorno do modelo vem dentro da chave `result` 
        JSONArray objs = (JSONArray) jsonObject.get("result"); 

        // Iterar sobre os objetos do `result`, onde cada um representa o resultado da classificação de um set
      
        for (Object _obj : objs) {
            hm = new HashMap<>();
            // Obtém o set de chaves do objeto e itera sobre eles, acessando o valor de cada um 
            // e o adicionando no dicionário em Java a ser retornado
            for (Object o : ((JSONObject) _obj).keySet()) {
                String key = (String) o;
                hm.put(key, ((JSONObject) _obj).get(key));
            }
            res.add(hm);
        }

        return res;
    }
}