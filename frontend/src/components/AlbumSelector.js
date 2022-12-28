import React from 'react';
import Button from '@mui/material/Button';

class AlbumSelector extends React.Component {
    constructor(props) {
        super(props);
        this.state = {albums : props.albums, questionSetter : props.questionSetter, number : props.number, questions : null};
    }

    selectAlbum = (album) => {
        fetch('http://localhost:8080/api/albums', {
            method : 'POST', 
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(album)
        })
        .then((response) => {
            if (!response.ok) throw new Error(response.status);
            fetch('http://localhost:8080/api/lyrics/' + this.state.number)
                .then((response) => {
                    if (!response.ok) throw new Error(response.headers);
                    else return response.json();
                })
                .then((list) => this.state.questionSetter(list));
            })
        .catch((error) => {console.log(error);});
    }

    render() {
        var albumList = this.state.albums
        .map(album => <div key={album.album_id}>
                <Button variant="text" onClick={() => this.selectAlbum(album)}>{album.album_name}</Button>
                <br></br>
            </div>);
        return(<div>
            {albumList}
        </div>);
    }
}

export default AlbumSelector;