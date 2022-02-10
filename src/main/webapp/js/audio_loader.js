let audioContext = new window.AudioContext();
let source
let current

window.addEventListener('load', function (){
    document.getElementById("audios")
            .addEventListener('click', function (event) {

                // delete
                if (event.target.className === 'delete') {
                    favorites_handle(event.target, 'delete');
                }

                // add to favorites buttons
                else if (event.target.className === 'add') {
                    favorites_handle(event.target);
                }

                // to prevent any other audio start playing
                else if (current == null || current === event.target) {

                    if (event.target.className === 'play') {
                        event.target.disabled = true;
                        play_audio(event.target);

                        // stop button
                        current = event.target.nextElementSibling;
                    } else {
                        source.stop(0);
                        // stop pushed
                        current = null;
                        event.target.parentElement.children[1].disabled = false;
                    }
                }
    })
});

function favorites_handle(target, action = 'add') {
    target.innerHTML = 'Delete from favorites';
    let title = target.parentElement.firstElementChild.innerHTML;

    let parameters = { title : title.trim() };

    let url = new URL(location.href);
    url.search = new URLSearchParams(parameters).toString();

    fetch(url.toString(), {
       method: 'PUT',
        headers: {
            'Action' : action
        }
    });
}

async function play_audio(target) {
    let title = target.parentElement.firstElementChild.innerHTML;
    await load_audio(title);
    source.start(0);
}

async function load_audio(title) {

    source = audioContext.createBufferSource();
    let parameters = { title : title.trim() };

    let url = new URL(location.href);
    url.search = new URLSearchParams(parameters).toString();

    let response = await fetch(url.toString(), {
        method: 'GET',
        headers: {
            'Type': 'ajax'
        }
    });

    let arrayBuffer = await response.arrayBuffer();

    return audioContext.decodeAudioData(arrayBuffer, function(decodedData){
        source.buffer = decodedData;
        source.connect(audioContext.destination);
    })
}