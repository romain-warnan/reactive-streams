const logs = document.querySelector('#logs')

const eventSource = new EventSource('logs/30')

eventSource.onmessage = event => {
    const element = document.createElement('li')
    element.innerHTML = event.data
    logs.appendChild(element)
}

eventSource.onerror = event => {
    eventSource.close()
}