#!/bin/bash

# Script para build e deploy da aplicação Docker

set -e

# Configurações
APP_NAME="evolve-backend"
DOCKER_USERNAME="xxxxxxx"  # Altere para seu username do Docker Hub
VERSION="latest"

echo "🚀 Iniciando build da aplicação..."

# 1. Limpar e buildar a aplicação
echo "📦 Building aplicação Spring Boot..."
./mvnw clean package -DskipTests

# 2. Verificar se o JAR foi criado
if [ ! -f "target/evolve-0.0.1-SNAPSHOT.jar" ]; then
    echo "❌ Erro: JAR não encontrado!"
    exit 1
fi

echo "✅ JAR criado com sucesso!"

# 3. Build da imagem Docker
echo "🐳 Building imagem Docker..."
docker build -t $DOCKER_USERNAME/$APP_NAME:$VERSION .
docker build -t $DOCKER_USERNAME/$APP_NAME:$(date +%Y%m%d-%H%M%S) .

echo "✅ Imagem Docker criada com sucesso!"

# 4. Testar a imagem localmente (opcional)
echo "🧪 Testando imagem localmente..."
docker run --rm -d -p 8080:8080 --name $APP_NAME-test $DOCKER_USERNAME/$APP_NAME:$VERSION

# Aguardar a aplicação iniciar
sleep 10

# Verificar se a aplicação está rodando
if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo "✅ Aplicação está funcionando!"
    docker stop $APP_NAME-test
else
    echo "❌ Erro: Aplicação não está respondendo!"
    docker stop $APP_NAME-test
    exit 1
fi

echo "🎉 Build concluído com sucesso!"
echo ""
echo "Para fazer o push para Docker Hub, execute:"
echo "  docker login"
echo "  docker push $DOCKER_USERNAME/$APP_NAME:$VERSION"
echo ""
echo "Para rodar localmente:"
echo "  docker run -p 8080:8080 $DOCKER_USERNAME/$APP_NAME:$VERSION"
