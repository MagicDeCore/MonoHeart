import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { INodeT } from 'app/shared/model/node-t.model';
import { getEntities as getNodeTs } from 'app/entities/node-t/node-t.reducer';
import { getEntity, updateEntity, createEntity, reset } from './detail-t.reducer';
import { IDetailT } from 'app/shared/model/detail-t.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDetailTUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDetailTUpdateState {
  isNew: boolean;
  nodeTId: string;
}

export class DetailTUpdate extends React.Component<IDetailTUpdateProps, IDetailTUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      nodeTId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getNodeTs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { detailTEntity } = this.props;
      const entity = {
        ...detailTEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/detail-t');
  };

  render() {
    const { detailTEntity, nodeTS, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="monoHeartApp.detailT.home.createOrEditLabel">Create or edit a DetailT</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : detailTEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="detail-t-id">ID</Label>
                    <AvInput id="detail-t-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="detail-t-name">
                    Name
                  </Label>
                  <AvField id="detail-t-name" type="text" name="name" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/detail-t" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  nodeTS: storeState.nodeT.entities,
  detailTEntity: storeState.detailT.entity,
  loading: storeState.detailT.loading,
  updating: storeState.detailT.updating,
  updateSuccess: storeState.detailT.updateSuccess
});

const mapDispatchToProps = {
  getNodeTs,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DetailTUpdate);
