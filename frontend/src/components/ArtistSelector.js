import React from 'react';
import Button from '@mui/material/Button';
import CircularProgress from '@mui/material/CircularProgress';
import Alert from '@mui/material/Alert';

class ArtistSelector extends React.Component {
    constructor(props) {
        super(props);
        this.state = {albumSetter : props.albumSetter, artists : null, loaded : false, errors : false};
    }

    componentDidMount() {
        fetch('http://localhost:8080/api/artists')
        .then((response) => {
            if (!response.ok) throw new Error(response.type);
            else return response.json();
        })
        .then((list) => this.setState({artists : list, loaded: true}))
        .catch((error) => {console.log(error); this.setState({errors : true, loaded: true});});
    }

    selectArtist = (artist) => {
        fetch('http://localhost:8080/api/artists', {
            method : 'POST', 
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(artist)
        })
        .then((response) => {
            if (!response.ok) throw new Error(response.status);
            fetch('http://localhost:8080/api/albums')
                .then((response) => {
                    if (!response.ok) throw new Error(response.headers);
                    else return response.json();
                })
                .then((list) => this.state.albumSetter(list));
            })
        .catch((error) => {console.log(error);});
    }

    render() {
        if (this.state.errors) return <Alert severity="error">Couldn't load artists</Alert>
        if(!this.state.loaded) return <CircularProgress />;

        var artistList = this.state.artists
        .map(artist => <div key={artist.artist_id}>
                <Button variant="text" onClick={() => this.selectArtist(artist)}>{artist.artist_name}</Button>
                <br></br>
            </div>);
        return(<div>
            {artistList}
        </div>);
    }
}

export default ArtistSelector;