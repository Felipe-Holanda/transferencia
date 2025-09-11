#!/bin/bash

# Script para fazer push da imagem para Docker Hub

set -e

# Configurações - ALTERE SEU USERNAME AQUI
DOCKER_USERNAME="xxxxxxxxx"  # ⚠️  ALTERE PARA SEU USERNAME DO DOCKER HUB
APP_NAME="evolve-backend"
VERSION="latest"

echo "🚀 Preparando para fazer push da imagem para Docker Hub..."
echo "📦 Imagem: $DOCKER_USERNAME/$APP_NAME:$VERSION"
echo ""

# Verificar se a imagem existe
if ! sudo docker images | grep -q "$DOCKER_USERNAME/$APP_NAME"; then
    echo "❌ Erro: Imagem $DOCKER_USERNAME/$APP_NAME não encontrada!"
    echo "Execute primeiro: ./build-docker.sh"
    exit 1
fi

echo "✅ Imagem encontrada!"
echo ""

echo "🔐 Fazendo login no Docker Hub..."
echo "Digite suas credenciais do Docker Hub:"
sudo docker login

echo ""
echo "📤 Fazendo push da imagem..."
sudo docker push $DOCKER_USERNAME/$APP_NAME:$VERSION

# Criar também uma tag com timestamp
TIMESTAMP=$(date +%Y%m%d-%H%M%S)
sudo docker tag $DOCKER_USERNAME/$APP_NAME:$VERSION $DOCKER_USERNAME/$APP_NAME:$TIMESTAMP
sudo docker push $DOCKER_USERNAME/$APP_NAME:$TIMESTAMP

echo ""
echo "🎉 Push concluído com sucesso!"
echo ""
echo "📋 Imagens disponíveis:"
echo "  - $DOCKER_USERNAME/$APP_NAME:latest"
echo "  - $DOCKER_USERNAME/$APP_NAME:$TIMESTAMP"
echo ""
echo "🌐 Para usar no Render.com:"
echo "  Imagem Docker: $DOCKER_USERNAME/$APP_NAME:latest"
echo ""
echo "🔧 Variáveis de ambiente necessárias no Render:"
echo "  PORT=8080"
echo "  SPRING_PROFILES_ACTIVE=prod"
echo "  JWT_SECRET=seu-jwt-secret-seguro-aqui"
echo "  JWT_EXPIRATION=3600000"
echo ""
echo "📖 Consulte o arquivo DOCKER.md para mais informações!"
