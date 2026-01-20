https://youtu.be/tXC9DQRWHUQ?si=pLzJGxF9LcKi_YlQ
 
> 보안적으로 vm이나 environment로 빼야하지만, 그냥 쓰자 \
> \
> spring.data.mongodb.uri=mongodb://${MONGO_ACCOUNT}@localhost:27017/notes \
> -DMONGO_ACCOUNT=app_user:app_pass1234 \
> \
> jwt.secret=${JWT_SECRET_BASE64} \
> -DJWT_SECRET_BASE64=lW9Q0HUm82whcsdx92FKbZdhRoS0gklXe6I/NvqT9BA= \


\
mongo db docker : ./infra/mongo-compose.yml 에 설정
>docker-compose -f ./infra/mongo-compose.yml up -d \
>docker-compose -f ./infra/mongo-compose.yml down

mongo-express접속 \
localhost:27021
