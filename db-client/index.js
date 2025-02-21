const net = require('net');

const client = net.createConnection({ port: 3000 }, () => {
    console.log('Connected to database server');

    // Create table
    client.write('CREATE users\n');

    // Insert data
    client.write('INSERT users 1 Alice 25\n');
    client.write('INSERT users 2 Bob 30\n');

    // Query data
    setTimeout(() => {
        client.write('SELECT users 1\n');
    }, 1000);
});

client.on('data', (data) => {
    console.log('Server:', data.toString());
});

client.on('end', () => {
    console.log('Disconnected from server.');
});
