## 성능테스트


### VEGETA 설치
``` shell 
vegeta attack -timeout=30s -duration=15s --rate=5000/1s \
 -targets=$1 -workers=100 | tee v_results.bin | vegeta report
 
$1 = request1.txt

================================= 
GET http://127.0.0.1:8080/users/1
GET http://127.0.0.1:8080/users/1
GET http://127.0.0.1:8080/users/1
=================================


vegeta attack -timeout=30s -duration=15s --rate=5000/1s \
 -targets=request1.txt -workers=100 | tee v_results.bin | vegeta report

```

### 캐쉬 사용 X

![이미지](/images/default.png)

- 성공률 57%  
- CPU 사용량도 120%가 넘어감.

### 캐쉬 사용 

![이미지](/images/redis1.png)
![이미지](/images/redis2.png)

- 성공률 100%
- CPU 사용량 12%.
