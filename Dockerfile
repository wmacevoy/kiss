FROM maven:3-openjdk-8

RUN apt-get update \
 && DEBIAN_FRONTEND=noninteractive apt-get install -y \
    build-essential \
    emacs-nox \
    dieharder \
    gpg \
    openssl \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

ARG USER_NAME=user
ARG USER_DIR=/home/$USER_NAME
ARG USER_UID=1000
ARG USER_GID=$USER_UID

# Create the user
RUN groupadd --gid $USER_GID $USER_NAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USER_NAME -d $USER_DIR \
    #
    # [Optional] Add sudo support. Omit if you don't need to install software after connecting.
    && apt-get update \
    && apt-get install -y sudo \
    && echo $USER_NAME ALL=\(root\) NOPASSWD:ALL > /etc/sudoers.d/$USER_NAME \
    && chmod 0440 /etc/sudoers.d/$USER_NAME

USER $USER_NAME
WORKDIR $USER_DIR

RUN git config --global user.email "wmacevoy@gmail.com"
RUN git config --global user.name "Warren MacEvoy"

ENTRYPOINT ["/bin/bash","-i","-c","\"$@\"","--"]